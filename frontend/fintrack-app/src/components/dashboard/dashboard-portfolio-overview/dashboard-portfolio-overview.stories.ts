import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardPortfolioOverview } from './dashboard-portfolio-overview.component';

const meta: Meta<DashboardPortfolioOverview> = {
  title: 'Components/DashboardPortfolioOverview',
  component: DashboardPortfolioOverview,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardPortfolioOverview>;

export const Primary: Story = {
  args: {},
};
