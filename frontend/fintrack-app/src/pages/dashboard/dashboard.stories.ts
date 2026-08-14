import type { Meta, StoryObj } from '@storybook/angular';
import { DashboardPage } from './dashboard.component';

const meta: Meta<DashboardPage> = {
  title: 'Components/DashboardPage',
  component: DashboardPage,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<DashboardPage>;

export const Primary: Story = {
  args: {},
};
