import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardChart } from './dashboard-chart.component';

const meta: Meta<DashboardChart> = {
  title: 'Components/DashboardChart',
  component: DashboardChart,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardChart>;

export const Primary: Story = {
  args: {},
};
