import type { Meta, StoryObj } from '@storybook/angular';
import { AddAssetModal } from './add-asset-modal.component';

const meta: Meta<AddAssetModal> = {
  title: 'Components/AddAssetModal',
  component: AddAssetModal,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<AddAssetModal>;

export const Primary: Story = {
  args: {},
};
